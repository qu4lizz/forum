import api from "./interceptor";

const baseUrl = "/oauth";

const getOauthUrl = async () => {
  const response = await api.get(baseUrl + "/url");
  return response.data;
};

const callbackToken = async (code: string) => {
  const response = await api.get(baseUrl + `/callback?code=${code}`);
  return response.data;
};

export default { getOauthUrl, callbackToken };
