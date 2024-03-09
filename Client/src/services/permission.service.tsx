import api from "../services/interceptor";

const baseUrl = "/permissions";

const getMy = async () => {
  const response = await api.get(baseUrl + "/my");
  return response.data;
};

export default { getMy };
