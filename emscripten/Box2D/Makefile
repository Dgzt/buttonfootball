# Makefile for generating a Box2D bitode to project using Emscripten.
#
# Box2D version: 2.2.1
#
# build with       emmake make
#

OPTS = -Os
LINK_OPTS = -O3 --llvm-lto 1

O = Box2D
OBJECTS = \
$(O)/Collision/b2BroadPhase.bc \
$(O)/Collision/b2CollideCircle.bc \
$(O)/Collision/b2CollideEdge.bc \
$(O)/Collision/b2CollidePolygon.bc \
$(O)/Collision/b2Collision.bc \
$(O)/Collision/b2Distance.bc \
$(O)/Collision/b2DynamicTree.bc \
$(O)/Collision/b2TimeOfImpact.bc \
$(O)/Collision/Shapes/b2ChainShape.bc \
$(O)/Collision/Shapes/b2CircleShape.bc \
$(O)/Collision/Shapes/b2EdgeShape.bc \
$(O)/Collision/Shapes/b2PolygonShape.bc \
$(O)/Common/b2BlockAllocator.bc \
$(O)/Common/b2Draw.bc \
$(O)/Common/b2Math.bc \
$(O)/Common/b2Settings.bc \
$(O)/Common/b2StackAllocator.bc \
$(O)/Common/b2Timer.bc \
$(O)/Dynamics/b2Body.bc \
$(O)/Dynamics/b2ContactManager.bc \
$(O)/Dynamics/b2Fixture.bc \
$(O)/Dynamics/b2Island.bc \
$(O)/Dynamics/b2World.bc \
$(O)/Dynamics/b2WorldCallbacks.bc \
$(O)/Dynamics/Contacts/b2ChainAndCircleContact.bc \
$(O)/Dynamics/Contacts/b2ChainAndPolygonContact.bc \
$(O)/Dynamics/Contacts/b2CircleContact.bc \
$(O)/Dynamics/Contacts/b2Contact.bc \
$(O)/Dynamics/Contacts/b2ContactSolver.bc \
$(O)/Dynamics/Contacts/b2EdgeAndCircleContact.bc \
$(O)/Dynamics/Contacts/b2EdgeAndPolygonContact.bc \
$(O)/Dynamics/Contacts/b2PolygonAndCircleContact.bc \
$(O)/Dynamics/Contacts/b2PolygonContact.bc \
$(O)/Dynamics/Joints/b2DistanceJoint.bc \
$(O)/Dynamics/Joints/b2FrictionJoint.bc \
$(O)/Dynamics/Joints/b2GearJoint.bc \
$(O)/Dynamics/Joints/b2Joint.bc \
$(O)/Dynamics/Joints/b2MouseJoint.bc \
$(O)/Dynamics/Joints/b2PrismaticJoint.bc \
$(O)/Dynamics/Joints/b2PulleyJoint.bc \
$(O)/Dynamics/Joints/b2RevoluteJoint.bc \
$(O)/Dynamics/Joints/b2RopeJoint.bc \
$(O)/Dynamics/Joints/b2WeldJoint.bc \
$(O)/Dynamics/Joints/b2WheelJoint.bc \
$(O)/Rope/b2Rope.bc

all: box2d.bc

%.bc: %.cpp
	$(CXX) $(OPTS) -I. $< -o $@

box2d.bc: $(OBJECTS)
	$(CXX) -o $@ $(OBJECTS)

clean:
	rm -f box2d.bc $(OBJECTS)
