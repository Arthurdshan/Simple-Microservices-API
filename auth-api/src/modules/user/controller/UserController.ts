import { Request, Response } from "express";
import { UserService } from "../service/UserService";

class UserController {

    async getAcessToken(req: Request, res: Response): Promise<Response> {
        const service = new UserService();
        const acessToken = await service.getAcessToken(req);

        return res.status(acessToken.status).json(acessToken);
    }

    async findBy (req: Request, res: Response): Promise<Response> {
        const service = new UserService();
        let data = await service.findBy(req);
        
        return res.status(data.status).json(data);
    }

    async save (req: Request, res: Response): Promise<Response> {
        const service = new UserService();
        let data = await service.save(req);

        return res.status(data.status).json(data);
    }
}

export default new UserController();