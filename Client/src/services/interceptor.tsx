import axios from "axios";
import { store } from "../redux";
import { logout } from "../redux/slices/userSlice";

const api = axios.create({
  baseURL: "/api",
});

api.interceptors.request.use(
  (config) => {
    const token = store.getState().user.token;

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (response) => response,

  async (error) => {
    if (error.response.data.startsWith("Logged out")) {
      store.dispatch(logout());
    }

    return Promise.reject(error);
  }
);

export default api;
