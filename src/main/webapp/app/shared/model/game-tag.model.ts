import { IGame } from '@/shared/model/game.model';

export interface IGameTag {
  id?: number;
  gameTagName?: string;
  description?: string;
  games?: IGame[];
}

export class GameTag implements IGameTag {
  constructor(public id?: number, public gameTagName?: string, public description?: string, public games?: IGame[]) {}
}
