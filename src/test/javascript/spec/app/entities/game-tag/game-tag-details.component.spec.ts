/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import GameTagDetailComponent from '@/entities/game-tag/game-tag-details.vue';
import GameTagClass from '@/entities/game-tag/game-tag-details.component';
import GameTagService from '@/entities/game-tag/game-tag.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('GameTag Management Detail Component', () => {
    let wrapper: Wrapper<GameTagClass>;
    let comp: GameTagClass;
    let gameTagServiceStub: SinonStubbedInstance<GameTagService>;

    beforeEach(() => {
      gameTagServiceStub = sinon.createStubInstance<GameTagService>(GameTagService);

      wrapper = shallowMount<GameTagClass>(GameTagDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { gameTagService: () => gameTagServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundGameTag = { id: 123 };
        gameTagServiceStub.find.resolves(foundGameTag);

        // WHEN
        comp.retrieveGameTag(123);
        await comp.$nextTick();

        // THEN
        expect(comp.gameTag).toBe(foundGameTag);
      });
    });
  });
});
