import { Component, Inject, Vue } from 'vue-property-decorator';

import { GameStatus, IGame } from '@/shared/model/game.model';
import GameService from './game.service';
import { ICharacter } from '@/shared/model/character.model';
import CharacterService from '@/entities/character/character.service';

@Component
export default class GameDetails extends Vue {
  @Inject('gameService') private gameService: () => GameService;
  @Inject('characterService') private characterService: () => CharacterService;
  private isUserAlreadyRegistered: boolean = false;

  public game: IGame = {};
  public userCharacters: ICharacter[] = [];
  public showCharacters: boolean = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gameId) {
        vm.retrieveGame(to.params.gameId);
      }
    });
  }

  public retrieveGame(gameId) {
    this.gameService()
      .find(gameId)
      .then(res => (this.game = res));
  }

  public previousState() {
    this.$router.go(-1);
  }

  public showUserCharacterWithFetch() {
    if (!this.isUserAlreadyRegistered && this.userCharacters.length === 0) {
      this.characterService()
        .retrieveCharactersByUserId(this.getUserId())
        .then(
          res =>
            (this.userCharacters = res.data.filter(character => character.isAlive && character.gameSystem.id === this.game.gameSystem.id))
        )
        .catch(reason => console.log(reason));
    }
    this.showCharacters = true;
  }

  private getUserId() {
    return this.$store.getters.account.id;
  }

  public joinWithCharacter(selectedUserCharacter: ICharacter) {
    if (this.game.characters === undefined) {
      this.game.characters = [selectedUserCharacter];
    } else {
      this.game.characters.push(selectedUserCharacter);
    }
    this.gameService()
      .update(this.game)
      .then(updatedGame => (this.game = updatedGame));

    this.isUserAlreadyRegistered = true;
  }

  public findUserCharacterInGame(): ICharacter {
    return this.game.characters.filter(character => character.user.id === this.getUserId())[0];
  }

  public leaveWithCharacter() {
    let registeredUserCharacter = this.findUserCharacterInGame();
    if (registeredUserCharacter !== undefined) {
      this.game.characters = this.game.characters.filter(character => character.id !== registeredUserCharacter.id);
      this.gameService()
        .update(this.game)
        .then(updated => (this.game = updated));

      this.isUserAlreadyRegistered = false;
    }
  }

  public hideUserCharacters(toShow: boolean) {
    this.showCharacters = toShow;
  }

  public isCharacterOfUser(gameCharacter: ICharacter): boolean {
    if (gameCharacter.user.id === this.getUserId()) {
      this.isUserAlreadyRegistered = true;
      return true;
    }
    return false;
  }

  public isUserRegistered(): boolean {
    return this.isUserAlreadyRegistered;
  }

  // todo: move to game class
  public getFreeSlotsCount(): number {
    return this.game.playersLimit - this.game.characters.length;
  }

  public isUserGM(): boolean {
    return this.game.user.id === this.getUserId();
  }

  public startGame() {
    if (this.isGamePending()) {
      this.game.status = GameStatus.IN_PROGRESS;
      this.gameService()
        .update(this.game)
        .then(updated => (this.game = updated));
    }
  }

  public endGame() {
    if (this.isGameInProgress()) {
      this.game.status = GameStatus.ENDED;
      this.gameService()
        .update(this.game)
        .then(updated => (this.game = updated));
    }
  }

  public cancelGame() {
    this.game.status = GameStatus.CANCELLED;
    this.gameService()
      .update(this.game)
      .then(updated => (this.game = updated));
  }

  public isGamePending(): boolean {
    return this.game.status === GameStatus.PENDING;
  }

  public isGameInProgress(): boolean {
    return this.game.status === GameStatus.IN_PROGRESS;
  }

  public isGameEnded(): boolean {
    return this.game.status === GameStatus.ENDED;
  }

  public isGameCancelled(): boolean {
    return this.game.status === GameStatus.CANCELLED;
  }
}
