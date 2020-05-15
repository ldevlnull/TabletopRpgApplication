import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGame } from '@/shared/model/game.model';
import GameService from './game.service';
import { ICharacter } from '@/shared/model/character.model';
import CharacterService from '@/entities/character/character.service';
import { debuglog, log } from 'util';

@Component
export default class GameDetails extends Vue {
  @Inject('gameService') private gameService: () => GameService;
  @Inject('characterService') private characterService: () => CharacterService;
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
      .then(res => {
        this.game = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public loadAndShowUserCharacters() {
    this.characterService()
      .retrieveCharactersByUserId(this.$store.getters.account.id)
      .then(res => {
        this.userCharacters = res.data.filter(character => character.isAlive && character.gameSystem.id === this.game.gameSystem.id);
      })
      .catch(reason => console.log(reason));

    this.showCharacters = true;
  }

  public joinWithCharacter(selectedUserCharacter: ICharacter) {
    this.game.characters.push(selectedUserCharacter);
    this.gameService()
      .update(this.game)
      .then(updatedGame => (this.game = updatedGame))
      .catch(reason => console.log(reason));
  }
}
