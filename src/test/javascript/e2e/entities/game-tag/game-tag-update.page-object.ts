import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class GameTagUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('trpgPlanningApplicationApp.gameTag.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  gameTagNameInput: ElementFinder = element(by.css('input#game-tag-gameTagName'));

  descriptionInput: ElementFinder = element(by.css('input#game-tag-description'));
}
