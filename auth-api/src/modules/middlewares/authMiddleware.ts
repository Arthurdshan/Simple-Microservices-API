import { Request, NextFunction, Response } from 'express';
import jwt from 'jsonwebtoken';
import { API_SECRET } from '../constants/secrets';

interface AuthUser {
    id: number;
    name: string;
    email: string;
}

interface TokenPayload {
    authUser: AuthUser;
    iat: number;
    exp: number;
}

export default function authMiddleware(req: Request, res: Response, next: NextFunction) {
    try {
        const { authorization } = req.headers;

        if (!authorization) {
            return res.sendStatus(401);
        }

        let acessToken = authorization.replace("Bearer ", "").trim();

        const data = jwt.verify(acessToken, API_SECRET);

        const { authUser } = data as TokenPayload;

        req.userId = authUser.id;

        return next();

    } catch {
        return res.sendStatus(401);
    }
}