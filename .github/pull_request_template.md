# Description

Please include a summary of the changes and the related issue. Provide context for why this change is needed and any background that helps reviewers understand the intent.

- Related issue: (e.g. #123)
- Type of change: (bugfix, feature, docs, refactor, tests, ci)

---

# Testing steps

List the steps a reviewer can follow to verify the changes locally or in a test environment. Include commands, environment variables (non-secret), and expected results.

Example:
1. Checkout the branch: git checkout feature/my-change
2. Install deps: pnpm install
3. Run typecheck: pnpm typecheck
4. Run tests: pnpm test
5. Run lint: pnpm lint
6. Start the app locally (if applicable): pnpm dev

Provide any additional verification details (logs to look for, endpoints to hit, screenshots, etc.).

---

# Checklist

Please check off items before requesting review. Remove items that don't apply.

- [ ] I have added/updated tests where applicable
- [ ] I have run pnpm typecheck locally and fixed type errors
- [ ] I have run pnpm lint and addressed lint issues
- [ ] All CI checks are passing
- [ ] I have updated relevant documentation (README, docs/) if needed
- [ ] I have added a rollback plan or described how to revert the change if necessary
- [ ] This PR does not include any secrets or sensitive data
- [ ] I have followed the CONTRIBUTING and CLA guidelines


Optional:
- Review notes / special instructions for reviewers:


<!-- You can customize this template per-repository. -->