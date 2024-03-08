export interface AuthUser {
  token?: string;
  username?: string;
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
