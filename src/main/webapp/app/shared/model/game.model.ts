import { IGameSystem } from '@/shared/model/game-system.model';
import { IGameTag } from '@/shared/model/game-tag.model';
import { ICharacter } from '@/shared/model/character.model';
import { IUser } from '@/shared/model/user.model';

export const enum GameStatus {
  PENDING = 'PENDING',
  IN_PROGRESS = 'IN_PROGRESS',
  CANCELLED = 'CANCELLED',
  ENDED = 'ENDED'
}

export interface IGame {
  id?: number;
  gameName?: string;
  playDate?: Date;
  playersLimit?: number;
  pictureURL?: string;
  description?: string;
  status?: GameStatus;
  gameSystem?: IGameSystem;
  tags?: IGameTag[];
  characters?: ICharacter[];
  user?: IUser;
  venue?: string;
}

export class Game implements IGame {
  constructor(
    public id?: number,
    public gameName?: string,
    public playDate?: Date,
    public playersLimit?: number,
    public pictureURL?: string,
    public description?: string,
    public status?: GameStatus,
    public gameSystem?: IGameSystem,
    public tags?: IGameTag[],
    public characters?: ICharacter[],
    public user?: IUser,
    public venue?: string
  ) {}
}
