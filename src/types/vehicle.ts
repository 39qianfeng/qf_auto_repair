export interface VehicleDTO {
  vehicleId?: number;
  customerId?: number;
  licensePlate: string;
  brand: string;
  model: string;
  vin: string;
  engineNo: string;
  mileage: number;
}

export interface VehicleVO {
  vehicleId: number;
  customerId: number;
  licensePlate: string;
  brand: string;
  model: string;
  vin: string;
  engineNo: string;
  mileage: number;
  customerName: string;
  customerType: string;
  repairOrderCount: number;
}

export interface VehicleQueryDTO {
  vehicleId?: number;
  customerId?: number;
  licensePlate?: string;
  brand?: string;
  model?: string;
  vin?: string;
  engineNo?: string;
  mileage?: number;
  customerType?: string;
  searchKey?: string;
  current: number;
  size: number;
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
