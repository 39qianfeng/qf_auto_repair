export interface Customer {
  customerId: number | null;
  name: string;
  phone: string;
  email: string;
  address: string;
  customerType: string;
  vehicleCount: number | null;
  repairOrderCount: number | null;
}

export interface Result<T> {
  code: number;
  msg: string | null;
  data: T;
}

export interface IPage<T> {
  records: T[];
  total: number;
  size: number;
  current: number;
  pages: number;
}
