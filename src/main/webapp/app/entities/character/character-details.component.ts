import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICharacter } from '@/shared/model/character.model';
import CharacterService from './character.service';

@Component
export default class CharacterDetails extends Vue {
  @Inject('characterService') private characterService: () => CharacterService;
  public character: ICharacter = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.characterId) {
        vm.retrieveCharacter(to.params.characterId);
      }
    });
  }

  public retrieveCharacter(characterId) {
    this.characterService()
      .find(characterId)
      .then(res => {
        this.character = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
