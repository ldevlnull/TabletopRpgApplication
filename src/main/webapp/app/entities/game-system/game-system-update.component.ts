import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IGameSystem, GameSystem } from '@/shared/model/game-system.model';
import GameSystemService from './game-system.service';

const validations: any = {
  gameSystem: {
    gameSystemName: {
      required
    },
    description: {}
  }
};

@Component({
  validations
})
export default class GameSystemUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('gameSystemService') private gameSystemService: () => GameSystemService;
  public gameSystem: IGameSystem = new GameSystem();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gameSystemId) {
        vm.retrieveGameSystem(to.params.gameSystemId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.gameSystem.id) {
      this.gameSystemService()
        .update(this.gameSystem)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('trpgPlanningApplicationApp.gameSystem.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.gameSystemService()
        .create(this.gameSystem)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('trpgPlanningApplicationApp.gameSystem.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveGameSystem(gameSystemId): void {
    this.gameSystemService()
      .find(gameSystemId)
      .then(res => {
        this.gameSystem = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
