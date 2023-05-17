import {Subscription} from "./Subscription";

export class Message {
  id: number;
  createdTime: string;
  authorName: string;
  chitchatId: number;
  message: string;
  subscriptionType: Subscription;

  constructor(id: number, createdTime: string, authorName: string, chitchatId: number, message: string, subscriptionType: Subscription) {
    this.id = id;
    this.createdTime = createdTime;
    this.authorName = authorName;
    this.chitchatId = chitchatId;
    this.message = message;
    this.subscriptionType = subscriptionType;
  }
}