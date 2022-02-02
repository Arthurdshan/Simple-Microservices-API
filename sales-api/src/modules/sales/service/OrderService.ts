import { PrismaClient } from "@prisma/client";
import { Request } from "express";
import { IOrder, IOrderConfirmation, IProductOrder } from "../../../constants/types";
import ProductClient from "../../product/client/ProductClient";
import { sendProductStockUpdateQueue } from "../../product/rabbitmq/productStockUpdateSender";
import { OrderException } from "../exception/OrderException";

export const PENDING = "PENDING";
export const ACCEPTED = "ACCEPTED";
export const REJECTED = "REJECTED";



class OrderService {
    private prisma: PrismaClient;

    constructor() {
        this.prisma = new PrismaClient();
    }

    async createOrder(request: Request) {
        try {
            let orderData = request.body;

            if (!orderData) {
                throw new OrderException(400, "The products must be informed");
            }

            const { userId } = request;
            const { authorization } = request.headers;

            const { products } = orderData;

            let order: IOrder = {
                status: PENDING,
                userId: userId,
                createdAt: new Date(),
                updatedAt: new Date(),
                products: products
            }
            
            await this.validateProductStock(order, authorization!);

            const newOrder = await this.prisma.order.create({ 
                data: {
                    userId: order.userId,
                    createdAt: order.createdAt,
                    updatedAt: order.updatedAt,
                    status: order.status
                }
            });
            
            products.forEach(async (productOrder: IProductOrder) => {
                await this.prisma.productOrder.create({
                    data: {
                        orderId: newOrder.id,
                        productId: productOrder.productId,
                        quantity: productOrder.quantity
                    } 
                });
            })

            sendProductStockUpdateQueue({ salesId: newOrder.id, products: order.products });

            return {
                status: 200,
                order: newOrder
            }
        } catch (error: any) {
            return {
                status: error.status ? error.status : 500,
                message: error.message
            }
        }
    }

    async updateOrder(orderMessage: string) {
        try {
            const order: IOrderConfirmation = JSON.parse(orderMessage);

            if (!order.salesId || !order.status) {
                throw new OrderException(400, "Missing fields");
            }

            let existingOrder = await this.prisma.order.findUnique({
                where: {
                    id: order.salesId
                }
            });

            if (order.status && order.status !== existingOrder?.status) {
                await this.prisma.order.update({
                    where: {
                        id: existingOrder!.id
                    },
                    data: {
                        status: order.status,
                        updatedAt: new Date()
                    }
                });
            }
        } catch (error) {
            console.error(error);
        }
    }

    async validateProductStock(order: IOrder, auth: string) {
        const stockIsOk = await ProductClient.checkProductStock(order.products, auth);
        if (!stockIsOk) {
            throw new OrderException(400, "Stock out of products!");
        } else {
            console.info("Stock is ok..");
        }
    }

    async findById(request: Request) {        
        try {
            const { id } = request.params;
            
            if (!id) {
                throw new OrderException(400, "The order Id must be informed.");
            }

            const existingOrder = await this.prisma.order.findUnique({
                where: {
                    id: id
                }
            });

            if (!existingOrder) {
                throw new OrderException(400, "The order was not found.");
            }

            const products = await this.prisma.productOrder.findMany({
                where: {
                    orderId: id
                },
                select: {
                    productId: true,
                    quantity: true
                }
            });
            
            const order: IOrder = {
                id: existingOrder.id,
                userId: existingOrder.userId,
                createdAt: existingOrder.createdAt,
                updatedAt: existingOrder.updatedAt,
                products: products,
                status: existingOrder.status
            }

            return {
                status: 200,
                order
            }
        } catch (error: any) {
            return {
                status: error.status ? error.status : 500,
                message: error.message
            }
        }
    }
};

export default new OrderService();