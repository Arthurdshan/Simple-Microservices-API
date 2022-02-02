import axios, { Axios, AxiosError } from "axios";
import { PRODUCT_API_URL } from "../../../constants/secrets";
import { IProductOrder } from "../../../constants/types";

class ProductClient {
    async checkProductStock(products: IProductOrder[], token: string) {
        try {            
            const API = axios.create({
                headers: { Authorization: token }
            });
            console.log("Sending request to product API with data: " + JSON.stringify(products));

            await API.post(`${PRODUCT_API_URL}/check_stock`, { products: products });

            return true;

        } catch (error) {
            console.error(error);
            
            return false;
        }
    }
}

export default new ProductClient();