/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CharacterDetailComponent from '@/entities/character/character-details.vue';
import CharacterClass from '@/entities/character/character-details.component';
import CharacterService from '@/entities/character/character.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Character Management Detail Component', () => {
    let wrapper: Wrapper<CharacterClass>;
    let comp: CharacterClass;
    let characterServiceStub: SinonStubbedInstance<CharacterService>;

    beforeEach(() => {
      characterServiceStub = sinon.createStubInstance<CharacterService>(CharacterService);

      wrapper = shallowMount<CharacterClass>(CharacterDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { characterService: () => characterServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCharacter = { id: 123 };
        characterServiceStub.find.resolves(foundCharacter);

        // WHEN
        comp.retrieveCharacter(123);
        await comp.$nextTick();

        // THEN
        expect(comp.character).toBe(foundCharacter);
      });
    });
  });
});
