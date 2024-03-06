import api from "./interceptor";

const baseUrl = "/users";

const getUsername = async () => {
  const response = await api.get(baseUrl + "/username");
  return response;
};

export default { getUsername };
