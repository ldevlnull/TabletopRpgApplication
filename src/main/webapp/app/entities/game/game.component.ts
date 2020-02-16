import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IGame } from '@/shared/model/game.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import GameService from './game.service';

@Component
export default class Game extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('gameService') private gameService: () => GameService;
  private removeId: number = null;
  public games: IGame[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllGames();
  }

  public clear(): void {
    this.retrieveAllGames();
  }

  public retrieveAllGames(): void {
    this.isFetching = true;

    this.gameService()
      .retrieve()
      .then(
        res => {
          this.games = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IGame): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeGame(): void {
    this.gameService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('trpgPlanningApplicationApp.game.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllGames();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
