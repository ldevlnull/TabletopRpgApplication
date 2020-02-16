import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import GameService from '../game/game.service';
import { IGame } from '@/shared/model/game.model';

import AlertService from '@/shared/alert/alert.service';
import { IGameTag, GameTag } from '@/shared/model/game-tag.model';
import GameTagService from './game-tag.service';

const validations: any = {
  gameTag: {
    gameTagName: {
      required
    },
    description: {}
  }
};

@Component({
  validations
})
export default class GameTagUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('gameTagService') private gameTagService: () => GameTagService;
  public gameTag: IGameTag = new GameTag();

  @Inject('gameService') private gameService: () => GameService;

  public games: IGame[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gameTagId) {
        vm.retrieveGameTag(to.params.gameTagId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.gameTag.id) {
      this.gameTagService()
        .update(this.gameTag)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('trpgPlanningApplicationApp.gameTag.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.gameTagService()
        .create(this.gameTag)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('trpgPlanningApplicationApp.gameTag.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveGameTag(gameTagId): void {
    this.gameTagService()
      .find(gameTagId)
      .then(res => {
        this.gameTag = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.gameService()
      .retrieve()
      .then(res => {
        this.games = res.data;
      });
  }
}
