import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGameTag } from '@/shared/model/game-tag.model';
import GameTagService from './game-tag.service';

@Component
export default class GameTagDetails extends Vue {
  @Inject('gameTagService') private gameTagService: () => GameTagService;
  public gameTag: IGameTag = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gameTagId) {
        vm.retrieveGameTag(to.params.gameTagId);
      }
    });
  }

  public retrieveGameTag(gameTagId) {
    this.gameTagService()
      .find(gameTagId)
      .then(res => {
        this.gameTag = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
