import static ratpack.groovy.Groovy.ratpack

import asset.pipeline.ratpack.AssetPipelineHandler
import asset.pipeline.ratpack.AssetPipelineModule

ratpack {
    serverConfig {
        json('assets.json')
    }
    bindings {
        moduleConfig(AssetPipelineModule, serverConfig.get("/assets", AssetPipelineModule.Config))
    }
    handlers {
        all(AssetPipelineHandler)
    }
}