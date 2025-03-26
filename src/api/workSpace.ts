// src/api/workspace.ts
import axiosInstance from "@/utils/axios";
import { Result } from "@/types/common";
import type {
  GoodsOverViewVO,
  RepairOrderOverViewVO,
  CustomerOverViewVO,
  BusinessDataVO,
} from "../types/workSpace";

const BASE_URL = "/api/workSpace";

export const workspaceApi = {
  getGoodsOverView: () => {
    return axiosInstance.get<Result<GoodsOverViewVO>>(
      `${BASE_URL}/overviewGoods`
    );
  },
  getRepairOrderOverView: () => {
    return axiosInstance.get<Result<RepairOrderOverViewVO>>(
      `${BASE_URL}/overviewRepairOrders`
    );
  },
  getCustomerOverView: () => {
    return axiosInstance.get<Result<CustomerOverViewVO>>(
      `${BASE_URL}/overviewCustomers`
    );
  },
  getBusinessData: () => {
    return axiosInstance.get<Result<BusinessDataVO>>(
      `${BASE_URL}/businessData`
    );
  },
};
