import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IGameSystem } from '@/shared/model/game-system.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import GameSystemService from './game-system.service';

@Component
export default class GameSystem extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('gameSystemService') private gameSystemService: () => GameSystemService;
  private removeId: number = null;
  public gameSystems: IGameSystem[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllGameSystems();
  }

  public clear(): void {
    this.retrieveAllGameSystems();
  }

  public retrieveAllGameSystems(): void {
    this.isFetching = true;

    this.gameSystemService()
      .retrieve()
      .then(
        res => {
          this.gameSystems = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IGameSystem): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeGameSystem(): void {
    this.gameSystemService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('trpgPlanningApplicationApp.gameSystem.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllGameSystems();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
