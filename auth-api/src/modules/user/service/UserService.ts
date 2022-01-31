import { Request } from "express";
import { getCustomRepository } from "typeorm";
import { UserException } from "../exception/UserException";
import { User } from "../model/User.entity";
import { UserRepository } from "../repository/UserRepository";

import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import { API_SECRET } from "../../constants/secrets";

interface IUser {
    id?: number;
    name?: string;
    email?: string;
    password?: string;
}

export class UserService {

    private userRepository: UserRepository;

    constructor() {
        this.userRepository = getCustomRepository(UserRepository);
    }

    async findBy(req: Request) {
        try {
            const { userId } = req;
            const data: IUser = req.body as IUser;
            
            const users: User[] = await this.userRepository.find({ where: data });

            if (users.length === 0) {
                throw new UserException(400, "No users were found");
            }

            users.forEach(user => {
                this.validateAuthUser(user.id, userId);
            })

            return {
                status: 200,
                users: users
            };

        } catch (err: any) {
            return {
                status: err.status ? err.status : 500,
                message: err.message
            }
        }
    }

    async save(req: Request) {
        try {
            const { name, email, password } = req.body;

            const userExists = await this.userRepository.findOne({ where: { email } });

            if (userExists) {
                throw new UserException(409, "user already exists");
            }

            const user = this.userRepository.create({ name, email, password });
            await this.userRepository.save(user);

            return {
                status: 201,
                userCreated: user
            }
        } catch (err: any) {
            return {
                status: err.status ? err.status : 500,
                message: err.message
            }
        }
    }

    private async validatePassword(password: string, hashPassword: string) {
        if (!await bcrypt.compare(password, hashPassword)) {
            throw new UserException(401, "Password does not match.");
        }
    }

    private validateAuthUser(userId: number, authUserId: number) {
        if (!authUserId || (userId !== authUserId)) {
            throw new UserException(403, "Acess denied");
        }
    }

    async getAcessToken(req: Request) {
        try {

            const { email, password } = req.body;

            if (!email || !password) {
                throw new UserException(401, "Email and password must be informed");
            }

            const user = await this.userRepository
                .createQueryBuilder("user")
                .where("user.email = :email", { email: email })
                .addSelect('user.password')
                .getOne();

            if (!user) {
                throw new UserException(401, "user not found");
            }

            await this.validatePassword(password, user.password);

            const authUser = {
                id: user.id,
                name: user.name,
                email: user.email
            };

            const acessToken = jwt.sign({ authUser }, API_SECRET, { expiresIn: "1d" });

            return {
                status: 200,
                user: authUser,
                acessToken: acessToken
            }

        } catch (err: any) {
            return {
                status: err.status ? err.status : 500,
                message: err.message
            }
        }

    }
}