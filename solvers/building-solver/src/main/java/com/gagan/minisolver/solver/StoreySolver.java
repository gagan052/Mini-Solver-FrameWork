package com.gagan.minisolver.solver;

import com.gagan.minisolver.annotation.SolverDefinition;
import com.gagan.minisolver.engine.SolverResult;
import com.gagan.minisolver.event.Command;
import com.gagan.minisolver.event.Event;
import com.gagan.minisolver.model.Criteria;
import com.gagan.minisolver.building.StoreyModel;

@SolverDefinition(
        command = Command.ADD,
        objectType = "Storey",
        priority = 1
)
public class StoreySolver
        implements Solver<StoreyModel, StoreyModel> {

    @Override
    public SolverResult<StoreyModel> solve(
            SolverContext context) {

        Event event = context.getEvent();
        Criteria criteria = event.getCriteria();

        int level = criteria
                .get("level", Number.class)
                .intValue();

        double height = criteria
                .get("height", Number.class)
                .doubleValue();

        StoreyModel storey =
                new StoreyModel(
                        event.getObjectId(),
                        level,
                        height
                );

        context.getModelContext().add(
                storey.getId(),
                storey
        );

        String buildingId =
                criteria.get("buildingId", String.class);

        if (buildingId == null) {
            return SolverResult.failure(
                    "buildingId is required for Storey"
            );
        }

        if (!context.getModelContext()
                .contains(buildingId)) {

            return SolverResult.failure(
                    "Building not found: " + buildingId
            );
        }

        Relationship relationship =
                new Relationship(
                        buildingId + "-contains-" + storey.getId(),
                        RelationshipTypes.CONTAINS,
                        buildingId,
                        storey.getId()
                );

        context.getModelContext()
                .addRelationship(relationship);

        System.out.println(
                "Storey created: " + storey
        );

        return SolverResult.success(
                "Storey created",
                storey
        );
    }
}