"use strict";

/**
 *  oprec controller
 */

const { createCoreController } = require("@strapi/strapi").factories;

module.exports = createCoreController("api::oprec.oprec", ({ strapi }) => ({
  async find(ctx) {
    ctx.query = { ...ctx.query, local: "en" };

    const currentUser = ctx.state.user;

    let data = await strapi.entityService.findMany("api::oprec.oprec", {
      ...ctx.query,
      populate: ["owner", "sies", "thumbnail"],
    });
    data = await this.sanitizeOutput(data, ctx);

    await Promise.all(
      data.map(async (it) => {
        const entry = await strapi.db
          .query("api::user-oprec.user-oprec")
          .findOne({
            where: { user: currentUser.id, oprec: it.id },
          });

        it.registered = entry !== null;
      })
    );

    return { data };
  },
  async findOne(ctx) {
    const {id}= ctx.params
    // some logic here
    // const response = await super.findOne(ctx);
    let response = await strapi.entityService.findOne("api::oprec.oprec", id, {
      ...ctx.query,
      populate: ["owner", "sies", "thumbnail"],
    });

    response = await this.sanitizeOutput(response, ctx);
    // console.log({response});
    // some more logic
  
    return {data: response};
  }
   
}));
