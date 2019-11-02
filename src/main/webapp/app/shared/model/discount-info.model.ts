export interface IDiscountInfo {
  id?: number;
  discountName?: string;
  discountType?: string;
  discountValue?: number;
  discountStatus?: string;
}

export class DiscountInfo implements IDiscountInfo {
  constructor(
    public id?: number,
    public discountName?: string,
    public discountType?: string,
    public discountValue?: number,
    public discountStatus?: string
  ) {}
}
