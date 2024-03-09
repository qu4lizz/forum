import api from "../services/interceptor";
import { CommentStatus } from "../types/types";

const baseUrl = "/comments";

const getAllPending = async () => {
  const response = await api.get(baseUrl + "/pending");
  return response.data;
};

const updateCommentStatus = async (id: number, status: CommentStatus) => {
  const response = await api.put(baseUrl + `/status/${id}`, status);
  return response.data;
};

const updateCommentContent = async (id: number, content: string) => {
  const response = await api.put(baseUrl + `/content/${id}`, { content });
  return response.data;
};

export default { getAllPending, updateCommentStatus, updateCommentContent };
