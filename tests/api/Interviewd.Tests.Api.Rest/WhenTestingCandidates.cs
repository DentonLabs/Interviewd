using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Interviewd.Tests.Api.Rest.LikenessExtensions;
using NUnit.Framework;

namespace Interviewd.Tests.Api.Rest
{
    [TestFixture]
    public class WhenTestingCandidates : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateACandidate()
        {
            var requestCandidate = Stubber.StubCandidateDto();

            var responseCandidate = await ApiClient.PostCandidate(requestCandidate)
                .AwaitGetSuccessfulResponse<CandidateDto>();

            responseCandidate.ToLikeness().ShouldEqual(requestCandidate);
        }

        [Test]
        public async Task ShouldBeAbleToGetAllCandidates()
        {
            var dbCandidates = await Arranger.CreateCandidates();

            var responseCandidateDtos = await ApiClient.GetAllCandidates()
                .AwaitGetSuccessfulResponse<IEnumerable<CandidateDto>>();

            var responseCandidates = Mapper.Map<IEnumerable<Candidate>>(responseCandidateDtos);

            responseCandidates = responseCandidates.Where(rc => dbCandidates.Any(dc => dc.Id == rc.Id));

            Assert.IsTrue(responseCandidates.CompareCollectionsUsingLikeness(dbCandidates));
        }

        [Test]
        public async Task ShouldBeAbleToGetCandidate()
        {
            var dbCandidate = await Arranger.CreateCandidate();

            var responseCandidateDto = await ApiClient.GetCandidate(dbCandidate.Id)
                .AwaitGetSuccessfulResponse<CandidateDto>();

            responseCandidateDto.ToLikeness(true).ShouldEqual(Mapper.Map<CandidateDto>(dbCandidate));
        }

        [Test]
        public async Task ShouldBeAbleToUpdateCandidate()
        {
            var candidateDto = Mapper.Map<CandidateDto>(await Arranger.CreateCandidate());

            var patchRequest = Stubber.StubCandidatePatchRequest();
            patchRequest.ApplyTo(candidateDto);

            (await ApiClient.PatchCandidate(candidateDto.Id, patchRequest))
                .EnsureSuccessStatusCode();

            var updatedCandidateDto = Mapper.Map<CandidateDto>(await Arranger.GetCandidate(candidateDto.Id));

            candidateDto.ToLikeness(true)
                .ShouldEqual(updatedCandidateDto);
        }
    }
}