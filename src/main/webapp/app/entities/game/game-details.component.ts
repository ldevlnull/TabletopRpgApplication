import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGame } from '@/shared/model/game.model';
import GameService from './game.service';

@Component
export default class GameDetails extends Vue {
  @Inject('gameService') private gameService: () => GameService;
  public game: IGame = {};

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
}
