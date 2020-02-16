import { IUser } from '@/shared/model/user.model';
import { IGameSystem } from '@/shared/model/game-system.model';
import { IGame } from '@/shared/model/game.model';

export interface ICharacter {
  id?: number;
  characterName?: string;
  isAlive?: boolean;
  user?: IUser;
  gameSystem?: IGameSystem;
  games?: IGame[];
}

export class Character implements ICharacter {
  constructor(
    public id?: number,
    public characterName?: string,
    public isAlive?: boolean,
    public user?: IUser,
    public gameSystem?: IGameSystem,
    public games?: IGame[]
  ) {
    this.isAlive = this.isAlive || false;
  }
}
