import { PrismaClient } from "@prisma/client";

export async function testData() {
    const prisma = new PrismaClient();

    await prisma.order.deleteMany({});
    await prisma.productOrder.deleteMany({});

    const order = await prisma.order.create({
        data: {
            createdAt: new Date(),
            updatedAt: new Date(),
            userId: 1,
            status: "APPROVED"
        }
    });

    await prisma.productOrder.create({
        data: {
            productId: 7,
            quantity: 1,
            orderId: order.id
        }
    });

    await prisma.productOrder.create({
        data: {
            productId: 8,
            quantity: 1,
            orderId: order.id
        }
    });

    await prisma.productOrder.create({
        data: {
            productId: 9,
            quantity: 4,
            orderId: order.id
        }
    });

    const result = await prisma.productOrder.findMany({
        where: {
            orderId: order.id
        }
    })
    console.log(order);
    console.log(result);
}
