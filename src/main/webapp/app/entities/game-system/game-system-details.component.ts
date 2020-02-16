import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGameSystem } from '@/shared/model/game-system.model';
import GameSystemService from './game-system.service';

@Component
export default class GameSystemDetails extends Vue {
  @Inject('gameSystemService') private gameSystemService: () => GameSystemService;
  public gameSystem: IGameSystem = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gameSystemId) {
        vm.retrieveGameSystem(to.params.gameSystemId);
      }
    });
  }

  public retrieveGameSystem(gameSystemId) {
    this.gameSystemService()
      .find(gameSystemId)
      .then(res => {
        this.gameSystem = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
