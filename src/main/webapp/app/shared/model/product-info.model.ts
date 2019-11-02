import { IDiscountInfo } from 'app/shared/model/discount-info.model';

export interface IProductInfo {
  id?: number;
  productName?: string;
  productType?: string;
  productStatus?: string;
  originPrice?: number;
  discountInfo?: IDiscountInfo;
}

export class ProductInfo implements IProductInfo {
  constructor(
    public id?: number,
    public productName?: string,
    public productType?: string,
    public productStatus?: string,
    public originPrice?: number,
    public discountInfo?: IDiscountInfo
  ) {}
}
