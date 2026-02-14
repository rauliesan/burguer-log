export interface BurguerLogin {
  email: string;
  password: string;
}

export interface BurguerRequest {
  name: string;
  address: string;
  town: string;
  email: string;
  password: string;
}

export interface BurguerResponse {
  id: number;
  name: string;
  address: string;
  town: string;
  email: string;
  mesas: number;
}
