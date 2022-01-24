import { Router } from "express";
import authMiddleware from "../../middlewares/authMiddleware";
import UserController from "../controller/UserController";

const router = Router();

router.post('/api/users/auth', UserController.getAcessToken);

router.use(authMiddleware);

router.post('/api/users', UserController.save);
router.get('/api/users', UserController.findBy);

export default router;