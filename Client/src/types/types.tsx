export interface AuthUser {
  token?: string;
  username?: string;
  role?: string;
}

export interface TopicCardInfo {
  id: number;
  name: string;
  image: any;
}

export interface TopicInfo {
  id: number;
  name: string;
  image: any;
  comments: any;
}

export interface CommentStatus {
  status: "APPROVED" | "REJECTED";
}

export const Role = {
  ADMIN: "ADMIN",
  MODERATOR: "MODERATOR",
  USER: "USER",
};

export const Status = {
  REQUESTED: "REQUESTED",
  APPROVED: "APPROVED",
  REJECTED: "REJECTED",
};

export interface PermissionDTO {
  id: number;
  update: boolean;
  write: boolean;
  delete: boolean;
  idUser: number;
  idTopic: number;
}

export interface UpdateUserRequest {
  id: number;
  role: string;
  status: string;
  permissions: PermissionDTO[];
}
