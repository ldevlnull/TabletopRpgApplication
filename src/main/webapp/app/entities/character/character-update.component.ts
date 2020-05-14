import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import GameSystemService from '../game-system/game-system.service';
import { IGameSystem } from '@/shared/model/game-system.model';

import GameService from '../game/game.service';
import { IGame } from '@/shared/model/game.model';

import AlertService from '@/shared/alert/alert.service';
import { ICharacter, Character } from '@/shared/model/character.model';
import CharacterService from './character.service';

const validations: any = {
  character: {
    characterName: {
      required
    },
    isAlive: {}
  }
};

@Component({
  validations
})
export default class CharacterUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('characterService') private characterService: () => CharacterService;
  public character: ICharacter = new Character();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('gameSystemService') private gameSystemService: () => GameSystemService;

  public gameSystems: IGameSystem[] = [];

  @Inject('gameService') private gameService: () => GameService;

  public games: IGame[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.characterId) {
        vm.retrieveCharacter(to.params.characterId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    this.character.isAlive = true;
    this.character.user = this.$store.getters.account;
    if (this.character.id) {
      this.characterService()
        .update(this.character)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('trpgPlanningApplicationApp.character.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.characterService()
        .create(this.character)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('trpgPlanningApplicationApp.character.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCharacter(characterId): void {
    this.characterService()
      .find(characterId)
      .then(res => {
        this.character = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.gameSystemService()
      .retrieve()
      .then(res => {
        this.gameSystems = res.data;
      });
    this.gameService()
      .retrieve()
      .then(res => {
        this.games = res.data;
      });
  }
}
