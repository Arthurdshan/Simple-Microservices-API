export interface IProductOrder {
    productId: number;
    quantity: number;
}

export interface IOrder {
    id?: string;
    userId: number;
    status: string;
    createdAt: Date;
    updatedAt: Date;
    products: IProductOrder[];
}

export interface IOrderConfirmation {
    salesId: string;
    status: string;
}