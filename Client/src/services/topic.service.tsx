import api from "../services/interceptor";

const baseUrl = "/topics";

const getAll = async () => {
  const response = await api.get(baseUrl);
  return response.data;
};

const getById = async (id: string) => {
  const response = await api.get(baseUrl + `/${id}`);
  return response.data;
};

export default { getAll, getById };
