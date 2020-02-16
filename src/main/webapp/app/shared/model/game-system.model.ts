export interface IGameSystem {
  id?: number;
  gameSystemName?: string;
  description?: string;
}

export class GameSystem implements IGameSystem {
  constructor(public id?: number, public gameSystemName?: string, public description?: string) {}
}
