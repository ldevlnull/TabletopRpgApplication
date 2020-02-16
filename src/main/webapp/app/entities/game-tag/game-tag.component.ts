import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IGameTag } from '@/shared/model/game-tag.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import GameTagService from './game-tag.service';

@Component
export default class GameTag extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('gameTagService') private gameTagService: () => GameTagService;
  private removeId: number = null;
  public gameTags: IGameTag[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllGameTags();
  }

  public clear(): void {
    this.retrieveAllGameTags();
  }

  public retrieveAllGameTags(): void {
    this.isFetching = true;

    this.gameTagService()
      .retrieve()
      .then(
        res => {
          this.gameTags = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IGameTag): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeGameTag(): void {
    this.gameTagService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('trpgPlanningApplicationApp.gameTag.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllGameTags();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
