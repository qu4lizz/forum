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
