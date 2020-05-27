import { IUser } from '@/shared/model/user.model';
import { IGameSystem } from '@/shared/model/game-system.model';
import { IGame } from '@/shared/model/game.model';

export interface ICharacter {
  id?: number;
  characterName?: string;
  isAlive?: boolean;
  isAvailable?: boolean;
  user?: IUser;
  gameSystem?: IGameSystem;
  games?: IGame[];
  pictureURL?: string;
}

export class Character implements ICharacter {
  constructor(
    public id?: number,
    public characterName?: string,
    public isAlive?: boolean,
    public isAvailable?: boolean,
    public user?: IUser,
    public gameSystem?: IGameSystem,
    public games?: IGame[],
    public pictureURL?: string
  ) {
    this.isAlive = this.isAlive || false;
    this.isAvailable = this.isAvailable || true;
  }
}
