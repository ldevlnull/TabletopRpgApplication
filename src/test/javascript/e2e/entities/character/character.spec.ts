/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import CharacterComponentsPage, { CharacterDeleteDialog } from './character.page-object';
import CharacterUpdatePage from './character-update.page-object';
import CharacterDetailsPage from './character-details.page-object';

import {
  clear,
  click,
  getRecordsCount,
  isVisible,
  selectLastOption,
  waitUntilAllDisplayed,
  waitUntilAnyDisplayed,
  waitUntilCount,
  waitUntilDisplayed,
  waitUntilHidden
} from '../../util/utils';

const expect = chai.expect;

describe('Character e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: CharacterUpdatePage;
  let detailsPage: CharacterDetailsPage;
  let listPage: CharacterComponentsPage;
  let deleteDialog: CharacterDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load Characters', async () => {
    await navBarPage.getEntityPage('character');
    listPage = new CharacterComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create Character page', async () => {
      await listPage.createButton.click();
      updatePage = new CharacterUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/trpgPlanningApplicationApp.character.home.createOrEditLabel/);
    });

    it('should create and save Characters', async () => {
      await updatePage.characterNameInput.sendKeys('characterName');
      expect(await updatePage.characterNameInput.getAttribute('value')).to.match(/characterName/);

      const selectedIsAlive = await updatePage.isAliveInput.isSelected();
      if (selectedIsAlive) {
        await updatePage.isAliveInput.click();
        expect(await updatePage.isAliveInput.isSelected()).to.be.false;
      } else {
        await updatePage.isAliveInput.click();
        expect(await updatePage.isAliveInput.isSelected()).to.be.true;
      }

      // await  selectLastOption(updatePage.userSelect);
      // await  selectLastOption(updatePage.gameSystemSelect);

      expect(await updatePage.saveButton.isEnabled()).to.be.true;
      await updatePage.saveButton.click();

      await waitUntilHidden(updatePage.saveButton);
      expect(await isVisible(updatePage.saveButton)).to.be.false;

      await waitUntilDisplayed(listPage.successAlert);
      expect(await listPage.successAlert.isDisplayed()).to.be.true;

      await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      expect(await listPage.records.count()).to.eq(beforeRecordsCount + 1);
    });

    describe('Details, Update, Delete flow', () => {
      after(async () => {
        const deleteButton = listPage.getDeleteButton(listPage.records.first());
        await click(deleteButton);

        deleteDialog = new CharacterDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/trpgPlanningApplicationApp.character.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details Character page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new CharacterDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit Character page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.characterNameInput.clear();
        await updatePage.characterNameInput.sendKeys('modified');
        expect(await updatePage.characterNameInput.getAttribute('value')).to.match(/modified/);

        const selectedIsAlive = await updatePage.isAliveInput.isSelected();
        if (selectedIsAlive) {
          await updatePage.isAliveInput.click();
          expect(await updatePage.isAliveInput.isSelected()).to.be.false;
        } else {
          await updatePage.isAliveInput.click();
          expect(await updatePage.isAliveInput.isSelected()).to.be.true;
        }

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
