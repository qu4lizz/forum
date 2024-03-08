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

const getPermissionsById = async (id: string) => {
  const response = await api.get(baseUrl + `/${id}` + "/permissions");
  return response.data;
};

const submitCommentToTopic = async (comment: any) => {
  const response = await api.post(baseUrl + "/comments", comment);
  return response.data;
};

export default { getAll, getById, getPermissionsById, submitCommentToTopic };
