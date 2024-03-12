import { UpdateUserRequest } from "../types/types";
import api from "./interceptor";

const baseUrl = "/users";

const getAll = async () => {
  const response = await api.get(baseUrl);
  return response.data;
};

const updateUser = async (id: number, obj: UpdateUserRequest) => {
  const response = await api.put(baseUrl + `/${id}`, obj);
  return response.data;
};

export default { getAll, updateUser };
