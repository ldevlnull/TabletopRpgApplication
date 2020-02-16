import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import GameSystemService from '../game-system/game-system.service';
import { IGameSystem } from '@/shared/model/game-system.model';

import GameTagService from '../game-tag/game-tag.service';
import { IGameTag } from '@/shared/model/game-tag.model';

import CharacterService from '../character/character.service';
import { ICharacter } from '@/shared/model/character.model';

import AlertService from '@/shared/alert/alert.service';
import { IGame, Game } from '@/shared/model/game.model';
import GameService from './game.service';

const validations: any = {
  game: {
    gameName: {
      required
    },
    playDate: {
      required
    },
    playersLimit: {},
    pictureURL: {},
    description: {},
    status: {
      required
    }
  }
};

@Component({
  validations
})
export default class GameUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('gameService') private gameService: () => GameService;
  public game: IGame = new Game();

  @Inject('gameSystemService') private gameSystemService: () => GameSystemService;

  public gameSystems: IGameSystem[] = [];

  @Inject('gameTagService') private gameTagService: () => GameTagService;

  public gameTags: IGameTag[] = [];

  @Inject('characterService') private characterService: () => CharacterService;

  public characters: ICharacter[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gameId) {
        vm.retrieveGame(to.params.gameId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.game.tags = [];
    this.game.characters = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.game.id) {
      this.gameService()
        .update(this.game)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('trpgPlanningApplicationApp.game.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.gameService()
        .create(this.game)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('trpgPlanningApplicationApp.game.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.game[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.game[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.game[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.game[field] = null;
    }
  }

  public retrieveGame(gameId): void {
    this.gameService()
      .find(gameId)
      .then(res => {
        res.playDate = new Date(res.playDate);
        this.game = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.gameSystemService()
      .retrieve()
      .then(res => {
        this.gameSystems = res.data;
      });
    this.gameTagService()
      .retrieve()
      .then(res => {
        this.gameTags = res.data;
      });
    this.characterService()
      .retrieve()
      .then(res => {
        this.characters = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
