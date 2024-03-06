import api from "./interceptor";

const baseUrl = "/auth";

const login = async (credentials: any) => {
  const response = await api.post(`${baseUrl}/login`, credentials);
  return response.data;
};

const register = async (credentials: any) => {
  const response = await api.post(`${baseUrl}/register`, credentials);
  return response.data;
};

const loginWithCode = async (codeObj: any) => {
  const response = await api.post(`${baseUrl}/login-code`, codeObj);
  return response.data;
};

export default { login, register, loginWithCode };
