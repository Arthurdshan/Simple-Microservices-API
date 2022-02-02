import { Router } from "express";
import OrderController from "../controller/OrderController";

const router = Router();

router.get('/api/order/:id', OrderController.findById);
router.post('/api/order/create', OrderController.createOrder);

export default router;