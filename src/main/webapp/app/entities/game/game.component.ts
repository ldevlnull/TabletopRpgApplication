import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IGame } from '@/shared/model/game.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import GameService from './game.service';
import { Authority } from '@/shared/security/authority';
import AccountService from '@/account/account.service';

@Component
export default class Game extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('gameService') private gameService: () => GameService;
  @Inject('accountService') private accountService: () => AccountService;
  private removeId: number = null;
  public games: IGame[] = [];
  public gamesByDays: Map<Date, IGame[]> = null;

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllGamesByDays();
  }

  public clear(): void {
    this.retrieveAllGamesByDays();
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

  public retrieveAllGamesByDays(): void {
    this.isFetching = true;

    this.gameService()
      .retrieveGroupedByDays()
      .then(
        res => {
          this.gamesByDays = res.data;
          this.isFetching = false;
          console.log(this.gamesByDays);
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
        // this.retrieveAllGames();
        this.retrieveAllGamesByDays();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public get moderator(): boolean {
    return this.authenticated && this.$store.getters.account.authorities.contains(Authority.MODERATOR);
  }

  public get gm(): boolean {
    return this.authenticated && this.$store.getters.account.authorities.contains(Authority.GAME_MASTER);
  }

  public hasAnyAuthority(authorities: any): boolean {
    return this.accountService().hasAnyAuthority(authorities);
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }
}
